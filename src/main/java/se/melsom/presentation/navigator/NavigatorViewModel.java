package se.melsom.presentation.navigator;

import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import se.melsom.event.EventType;
import se.melsom.event.ModelElement;
import se.melsom.event.ModelEvent;
import se.melsom.event.ModelEventListener;
import se.melsom.io.pdf.PDFProcessor;
import se.melsom.io.pdf.PDFRenderer;
import se.melsom.model.Competition;
import se.melsom.model.document.MatchProtocol;
import se.melsom.model.program.ProgramStage;
import se.melsom.model.program.ProgramString;
import se.melsom.model.program.StageTeam;
import se.melsom.model.score.Scoreboard;
import se.melsom.model.score.StageScore;
import se.melsom.presentation.ViewModel;
import se.melsom.settings.ApplicationSettings;
import se.melsom.settings.WindowSettings;

public class NavigatorViewModel extends ViewModel implements TreeSelectionListener, ModelEventListener {
	private static Logger logger = Logger.getLogger(NavigatorViewModel.class);

	private Competition competition;
	private NavigatorView view;
	private MatchStageView matchStageView;
	private StageTeamView teamView;
	private StringView stringView;

	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Tävling");
	private DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

	public NavigatorViewModel(Competition competition) {
		this.competition = competition;

		WindowSettings settings = ApplicationSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 10, 10, 600, 400, true);
			
			ApplicationSettings.singleton().addWindowSettings(settings);
		}
		
		view = new NavigatorView(treeModel, this);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);

		matchStageView = new MatchStageView();
		teamView = new StageTeamView();
		stringView = new StringView();
	}

	public JInternalFrame getInternalFrame() {
		return view;
	}

	@Override
	public JComponent getView() {
		return view;
	}

	public void addViewModelToTree(ViewModel viewModel) {
		rootNode.add(new DefaultMutableTreeNode(viewModel));
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode object = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();

		if (object instanceof MatchStageViewModel) {
			MatchStageViewModel viewModel = (MatchStageViewModel) object;

			viewModel.updateView(matchStageView);
			matchStageView.setViewModel(viewModel);
			view.setViewComponent(matchStageView);
		} else if (object instanceof StageTeamViewModel) {
			StageTeamViewModel viewModel = (StageTeamViewModel) object;

			viewModel.updateView(teamView);
			teamView.setViewModel(viewModel);
			view.setViewComponent(teamView);
		} else if (object instanceof StringViewModel) {
			StringViewModel viewModel = (StringViewModel) object;

			viewModel.updateView(stringView);
			stringView.setViewModel(viewModel);
			view.setViewComponent(stringView);
		} else if (object.getUserObject() instanceof ViewModel) {
			ViewModel viewModel = (ViewModel) object.getUserObject();

			view.setViewComponent(viewModel.getView());
		} else {
			view.setViewComponent(null);
		}
		
		WindowSettings settings = ApplicationSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings != null) {
			view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		}
	}

	@Override
	public void handleEvent(ModelEvent event) {
		ModelElement source = event.getSource();
		
		if (source instanceof ProgramStage) {
			logger.debug("handle program stage event(" + event + ")");
			ProgramStage stage = (ProgramStage) source;

			rootNode.add(new MatchStageViewModel(stage));
			treeModel.nodeStructureChanged(rootNode);
		} else if (source instanceof StageTeam) {
			logger.debug("handle stage team event(" + event + ")");
			StageTeam team = (StageTeam) event.getSource();
			ProgramStage stage = (ProgramStage) event.getSource().getParent();
			MatchStageViewModel stageViewModel = getStageViewModel(stage);

			if (stageViewModel == null) {
				logger.warn("View model is null for stage: " + stage);
				return;
			}

			if (event.getType() == EventType.MODEL_ELEMENT_ADDED) {
				logger.debug("Adding team.");

				logger.debug("Adding team node.");
				StageTeamViewModel teamViewModel = new StageTeamViewModel(team);

				stageViewModel.add(teamViewModel);

				for (ProgramString string : stage.getStrings()) {
					StringViewModel stringViewModel = new StringViewModel(string, team, competition.getScoreboard());

					teamViewModel.add(stringViewModel);
				}

				treeModel.nodeStructureChanged(stageViewModel);
			}
		}
	}


	public void printProtocol() {
		logger.debug("Print protocol");
		if (view.getSelectedNode() instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.getSelectedNode();
			
			logger.debug("Print protocol for " + node);

			if (node instanceof MatchStageViewModel) {
				logger.debug("Print stage protocol");
				MatchStageViewModel stageViewModel = (MatchStageViewModel) node;
				MatchProtocol protocol = new MatchProtocol(stageViewModel.getStage());				
				PDFProcessor processor = new PDFProcessor();
				PDFRenderer renderer = new PDFRenderer();
				
				protocol.process(processor);
				protocol.render(renderer);
				
				try {
					renderer.save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void printResult() {
		logger.debug("Print result");
		if (view.getSelectedNode() instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) view.getSelectedNode();
			
			logger.debug("Print result for " + node);

			if (rootNode.equals(node)) {
				logger.debug("Print total result");
			} else if (node instanceof MatchStageViewModel) {
				logger.debug("Print stage result");
				MatchStageViewModel stageViewModel = (MatchStageViewModel) node;
			}
		}
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	private MatchStageViewModel getStageViewModel(ProgramStage stage) {
		for (int childIndex = 0; childIndex < rootNode.getChildCount(); childIndex++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) rootNode.getChildAt(childIndex);

			if (child instanceof MatchStageViewModel) {
				MatchStageViewModel stageViewModel = (MatchStageViewModel) child;

				if (stage.equals(stageViewModel.getStage())) {
					return stageViewModel;
				}
			}
		}

		return null;
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		ApplicationSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		ApplicationSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		ApplicationSettings.singleton().setWindowVisible(getWindowName(), true);	
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		ApplicationSettings.singleton().setWindowVisible(getWindowName(), false);	
	}

	String getWindowName() {
		return NavigatorView.class.getSimpleName();
	}
}
